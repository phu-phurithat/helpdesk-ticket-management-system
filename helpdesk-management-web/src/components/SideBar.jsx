function SideBar({ isSideBarOpen, setIsSideBarOpen, setVisibleStyled }) {
  return (
    <div
      className={` fixed top-0 left-0 h-full w-64 bg-gray-800 shadow-lg text-white transform ${
        isSideBarOpen ? "translate-x-0" : "-translate-x-64"
      } transition-transform duration-300 ease-in-out z-2`}
    >
      {/* Close Button */}
      <button
        className="absolute top-4 right-4 text-white"
        onClick={() => setIsSideBarOpen(false)}
      >
        <i className="fa-solid fa-xmark"></i>
      </button>

      <div className="p-4">
        <h2 className="text-xl font-bold">Visible Menu</h2>
        <ul className="mt-4 space-y-2">
          <li
            className="hover:bg-gray-700 p-2 rounded w-full cursor-pointer"
            onClick={() => {
              setVisibleStyled("board");
              setIsSideBarOpen(false);
            }}
          >
            KanBan Board
          </li>
          <li
            className="hover:bg-gray-700 p-2 rounded w-full cursor-pointer"
            onClick={() => {
              setVisibleStyled("table");
              setIsSideBarOpen(false);
            }}
          >
            Table
          </li>
        </ul>
      </div>
    </div>
  );
}

export default SideBar;
