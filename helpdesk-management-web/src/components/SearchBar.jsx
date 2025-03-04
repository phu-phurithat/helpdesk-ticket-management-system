function SearchBar({ setSearchQuery }) {
  return (
    <input
      type="text"
      placeholder="Search title"
      className="border py-2 px-4 rounded-full w-64 focus:outline-none"
      onChange={(e) => setSearchQuery(e.target.value)}
    />
  );
}

export default SearchBar;
