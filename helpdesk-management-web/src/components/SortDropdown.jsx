import React from "react";

const SortDropdown = ({ sortBy, setSortBy }) => {
  return (
    <div className="flex items-center gap-1">
      Sort By
      <select
        className="rounded-lg font-medium appearance-auto"
        value={sortBy}
        onChange={(e) => setSortBy(e.target.value)}
      >
        <option value="earliest">Earliest Updated</option>
        <option value="latest">Latest Updated</option>
      </select>
    </div>
  );
};

export default SortDropdown;
