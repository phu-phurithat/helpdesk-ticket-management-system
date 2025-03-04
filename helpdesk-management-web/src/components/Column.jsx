import { Droppable, Draggable } from "@hello-pangea/dnd";
import TicketCard from "./TicketCard";

const Column = ({ status, tickets, onSave }) => {
  const statusBodyColors = {
    Pending: "bg-yellow-100",
    Accepted: "bg-blue-100",
    Resolved: "bg-green-100",
    Rejected: "bg-red-100",
  };
  const statusColors = {
    Pending: "bg-yellow-200",
    Accepted: "bg-blue-200",
    Resolved: "bg-green-200",
    Rejected: "bg-red-200",
  };

  return (
    <Droppable droppableId={status}>
      {(provided) => (
        <div
          ref={provided.innerRef}
          {...provided.droppableProps}
          className={`${statusBodyColors[status]} rounded-lg h-[100%] overflow-scroll`}
        >
          <h2
            className={`font-bold text-lg mb-2 h-10 px-4 items-center flex 
          ${statusColors[status]}`}
          >
            {status}
          </h2>
          <div className="overflow-auto flex flex-col h-auto min-h-[50%] gap-y-1 px-4 z-2">
            {tickets.map((ticket, index) => (
              <Draggable
                key={ticket.id}
                draggableId={ticket.id.toString()}
                index={index}
              >
                {(provided, snapshot) => (
                  <div
                    ref={provided.innerRef}
                    {...provided.draggableProps}
                    {...provided.dragHandleProps}
                    className={`transition-transform ${
                      snapshot.isDragging ? "scale-105" : ""
                    }`}
                  >
                    <TicketCard
                      key={ticket.id}
                      ticket={ticket}
                      onSave={onSave}
                    />
                  </div>
                )}
              </Draggable>
            ))}
            {provided.placeholder}
          </div>
        </div>
      )}
    </Droppable>
  );
};

export default Column;
