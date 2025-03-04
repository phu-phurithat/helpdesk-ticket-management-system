function PageSizeSelector({ pageSize, setPageSize }) {
  return (
    <div className="flex items-center gap-2">
      <label htmlFor="pageSize" className="text-sm font-medium">
        Items:
      </label>
      <select
        id="pageSize"
        value={pageSize}
        onChange={(e) => {
          setPageSize(parseInt(e.target.value, 10));
        }}
        className="border rounded-md px-2 py-1"
      >
        <option value={5}>5</option>
        <option value={10}>10</option>
        <option value={20}>20</option>
        <option value={50}>50</option>
      </select>
    </div>
  );
}

export default PageSizeSelector;
