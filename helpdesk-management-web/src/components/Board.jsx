import { useState, useEffect } from "react";
import { DragDropContext } from "@hello-pangea/dnd";
import SearchBar from "./SearchBar";
import SortDropdown from "./SortDropdown";
import Column from "./Column";
import StatusDropdown from "./StatusDropdown";
import {
  getSortedAndPagedTickets,
  getSortedAndFilteredAndPagedTickets,
  updateTicket,
  createTicket,
  updateTicketStatus,
} from "../services/api";
import PageSelector from "./PageSelector";
import PageSizeSelector from "./PageSizeSelector";
import TicketModal from "./TicketModal";

function Board() {
  const [tickets, setTickets] = useState([]);
  const [loading, setLoading] = useState(false);
  const [searchQuery, setSearchQuery] = useState("");
  const [sortBy, setSortBy] = useState("earliest");
  const [selectedStatus, setSelectedStatus] = useState("any");
  const [currentPage, setCurrentPage] = useState(1);
  const [pageSize, setPageSize] = useState(20);
  const [totalPages, setTotalPages] = useState(0);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const columns = ["Pending", "Accepted", "Resolved", "Rejected"];

  const filteredTicket =
    tickets !== null
      ? tickets.filter((ticket) =>
          ticket.title.toLowerCase().includes(searchQuery.toLowerCase())
        )
      : [];

  const fetchTickets = async () => {
    setLoading(true);
    try {
      let data;
      if (selectedStatus === "any") {
        data =
          sortBy === "earliest"
            ? await getSortedAndPagedTickets(
                "lastUpdate",
                "desc",
                currentPage - 1,
                pageSize
              )
            : await getSortedAndPagedTickets(
                "lastUpdate",
                "asc",
                currentPage - 1,
                pageSize
              );
      } else {
        data =
          sortBy === "earliest"
            ? await getSortedAndFilteredAndPagedTickets(
                "lastUpdate",
                "desc",
                selectedStatus,
                currentPage - 1,
                pageSize
              )
            : await getSortedAndFilteredAndPagedTickets(
                "lastUpdate",
                "asc",
                selectedStatus,
                currentPage - 1,
                pageSize
              );
      }
      setTickets(data.content);
      setTotalPages(data.totalPages);
    } catch (error) {
      console.error("Error fetching data:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleSave = async (updatedTicket) => {
    try {
      const response = await updateTicket(updatedTicket.id, updatedTicket);
      setTickets((prevTickets) =>
        prevTickets.map((t) => (t.id === response.id ? response : t))
      );
    } catch (error) {
      console.error("Error updating ticket:", error);
    }
  };

  const handleCreateTicket = async (newTicket) => {
    try {
      const response = await createTicket(newTicket);
      setTickets((prevTickets) => [...prevTickets, response]);
    } catch (error) {
      console.error("Error creating ticket:", error);
    }
  };

  useEffect(() => {
    fetchTickets();
  }, [sortBy, selectedStatus, currentPage, pageSize]);

  const onDragEnd = async (result) => {
    const { source, destination } = result;

    if (!destination) return; // Dropped outside a column

    const draggedTicket = tickets.find(
      (t) => t.id.toString() === result.draggableId
    );
    if (!draggedTicket) return;

    const newStatus = destination.droppableId.toUpperCase();
    if (draggedTicket.status === newStatus) return; // No change

    // Update UI Optimistically
    const updatedTickets = tickets.map((ticket) =>
      ticket.id === draggedTicket.id ? { ...ticket, status: newStatus } : ticket
    );
    setTickets(updatedTickets);

    // Call API to update ticket status
    try {
      const body = { status: newStatus };
      await updateTicketStatus(draggedTicket.id, body);
    } catch (error) {
      console.error("Failed to update ticket status:", error);
      fetchTickets(); // Revert if API fails
    }
  };

  return (
    <DragDropContext onDragEnd={onDragEnd}>
      <section className="px-12 pb-4 flex flex-col h-11/12 overflow-hidden relative">
        <div className="flex flex-row justify-between items-center">
          <div className="flex flex-row py-4 gap-4">
            <SearchBar setSearchQuery={setSearchQuery} />
            <div className="flex gap-4">
              <SortDropdown sortBy={sortBy} setSortBy={setSortBy} />
              <StatusDropdown
                selectedStatus={selectedStatus}
                setSelectedStatus={setSelectedStatus}
              />
              <div
                className="w-8 h-8 bg-white shadow-sm shadow-black/50 rounded-xl flex items-center justify-center cursor-pointer hover:bg-gray-100"
                onClick={() => setIsModalOpen(true)}
              >
                <i className="fa-solid fa-plus text-emerald-600"></i>
              </div>
            </div>
          </div>
          <div className="flex gap-4">
            <PageSelector
              currentPage={currentPage}
              setCurrentPage={setCurrentPage}
              totalPages={totalPages}
            />
            <PageSizeSelector pageSize={pageSize} setPageSize={setPageSize} />
          </div>
        </div>
        <div className="grid grid-cols-4 gap-4 h-[90%]">
          {columns.map((status) => (
            <Column
              key={status}
              status={status}
              tickets={filteredTicket.filter(
                (ticket) => ticket.status === status?.toUpperCase()
              )}
              onSave={handleSave}
            />
          ))}
        </div>
        {isModalOpen && (
          <TicketModal
            ticket={null}
            onClose={() => setIsModalOpen(false)}
            onSave={handleCreateTicket}
          />
        )}
      </section>
    </DragDropContext>
  );
}

export default Board;
