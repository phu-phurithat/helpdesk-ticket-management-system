const StatusDropdown = ({ selectedStatus, setSelectedStatus }) => {
  return (
    <div className="flex items-center gap-1">
      Status:
      <select
        value={selectedStatus}
        onChange={(e) => setSelectedStatus(e.target.value)}
        className="rounded-lg font-medium"
      >
        <option value="any">Any</option>
        <option value="pending">Pending</option>
        <option value="accepted">Accepted</option>
        <option value="resolved">Resolved</option>
        <option value="rejected">Rejected</option>
      </select>
    </div>
  );
};

export default StatusDropdown;
