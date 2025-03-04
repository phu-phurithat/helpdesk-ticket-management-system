import { useState, useEffect } from "react";
import SearchBar from "./SearchBar";
import SortDropdown from "./SortDropdown";
import StatusDropdown from "./StatusDropdown";
import {
  getSortedAndPagedTickets,
  getSortedAndFilteredAndPagedTickets,
  updateTicket,
} from "../services/api";
import PageSelector from "./PageSelector";
import PageSizeSelector from "./PageSizeSelector";
import TicketModal from "./TicketModal";

function Table() {
  const [tickets, setTickets] = useState([]);
  const [loading, setLoading] = useState(false);
  const [searchQuery, setSearchQuery] = useState("");
  const [sortBy, setSortBy] = useState("earliest");
  const [selectedStatus, setSelectedStatus] = useState("any");
  const [currentPage, setCurrentPage] = useState(1);
  const [pageSize, setPageSize] = useState(20);
  const [totalPages, setTotalPages] = useState(0);

  const [selectedTicket, setSelectedTicket] = useState(null);

  const columns = ["id", "title", "description", "contact", "status"];

  const borderColors = {
    PENDING: "border-yellow-500",
    ACCEPTED: "border-blue-500",
    RESOLVED: "border-green-500",
    REJECTED: "border-red-500",
  };

  const statusBgColors = {
    PENDING: "bg-yellow-100",
    ACCEPTED: "bg-blue-100",
    RESOLVED: "bg-green-100",
    REJECTED: "bg-red-100",
  };

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

  useEffect(() => {
    fetchTickets();
  }, [sortBy, selectedStatus, currentPage, pageSize]);

  const handleRowClick = (ticket) => {
    setSelectedTicket(ticket);
  };

  const handleSave = async (updatedTicket) => {
    try {
      const response = await updateTicket(updatedTicket.id, updatedTicket);
      setTickets((prevTickets) =>
        prevTickets.map((t) => (t.id === response.id ? response : t))
      );
      setSelectedTicket(null); // Close modal after update
    } catch (error) {
      console.error("Error updating ticket:", error);
    }
  };

  return (
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
      <div className="overflow-auto max-h-full border rounded-lg shadow-md">
        <table className="w-full table-fixed">
          <thead className="border-b font-medium sticky top-0">
            <tr className="bg-blue-100">
              <th scope="col" className="w-1/12 px-6 py-4 text-left">
                Ticket
              </th>
              <th scope="col" className="w-2/12 px-6 py-4 text-left">
                Title
              </th>
              <th scope="col" className="w-5/12 px-6 py-4 text-left">
                Description
              </th>
              <th scope="col" className="w-2/12 px-6 py-4 text-left">
                Contact
              </th>
              <th scope="col" className="w-2/12 px-6 py-4 text-left mx-8">
                Status
              </th>
            </tr>
          </thead>
          <tbody>
            {tickets.map((ticket, index) => (
              <tr
                key={index}
                className="border-b hover:bg-gray-100"
                onClick={() => handleRowClick(ticket)}
              >
                {columns.map((col) => (
                  <td key={col} className="truncate py-2 px-4">
                    {col === "status" ? (
                      <span
                        className={`text-xs border px-2 py-1 rounded-full mt-2 inline-block ${
                          borderColors[ticket.status]
                        } ${statusBgColors[ticket.status]}`}
                      >
                        {ticket.status}
                      </span>
                    ) : (
                      ticket[col]
                    )}
                  </td>
                ))}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      {/* Render Modal */}
      {selectedTicket && (
        <TicketModal
          ticket={selectedTicket}
          onClose={() => setSelectedTicket(null)}
          onSave={handleSave}
        />
      )}
    </section>
  );
}

export default Table;
