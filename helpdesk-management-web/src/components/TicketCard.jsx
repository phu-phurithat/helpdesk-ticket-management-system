import { useState } from "react";
import TicketModal from "./TicketModal";

const TicketCard = ({ ticket, onSave }) => {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const cardColors = {
    PENDING: "hover:bg-yellow-50",
    ACCEPTED: "hover:bg-blue-50",
    RESOLVED: "hover:bg-green-50",
    REJECTED: "hover:bg-red-50",
  };

  const borderColors = {
    PENDING: "border-yellow-500",
    ACCEPTED: "border-blue-500",
    RESOLVED: "border-green-500",
    REJECTED: "border-red-500",
  };

  return (
    <div
      className={`bg-white p-3 rounded-lg shadow-md mb-2 justify-between flex flex-row items-center ${
        cardColors[ticket.status]
      }`}
      onClick={() => setIsModalOpen(true)}
    >
      <div className="w-[90%]">
        <p className="text-sm font-bold">#{ticket.id}</p>
        <p className="text-sm truncate">{ticket.title}</p>
        <p className="text-sm text-gray-500 truncate">{ticket.contact}</p>
        <span
          className={`text-xs border px-2 py-1 rounded-full mt-2 inline-block ${
            borderColors[ticket.status]
          }`}
        >
          {ticket.status}
        </span>
      </div>
      <i className="fa-solid fa-light fa-bars"></i>
      {isModalOpen && (
        <TicketModal
          ticket={ticket}
          onClose={() => setIsModalOpen(false)}
          onSave={onSave}
        />
      )}
    </div>
  );
};

export default TicketCard;
