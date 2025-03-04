import { useState } from "react";
import SideBar from "./SideBar";

function Navbar({ setVisibleStyled }) {
  const [isSideBarOpen, setIsSideBarOpen] = useState(false);

  return (
    <nav className="w-full h-1/12 flex justify-between py-4 px-12 border-b-2 sticky top-0 bg-white z-1">
      <div className="flex items-center space-x-5">
        <div
          className="hover:bg-gray-100 p-1.5 rounded-sm"
          onClick={() => setIsSideBarOpen(true)}
        >
          <i className="fa-duotone fa-solid fa-bars fa-2xl"></i>
        </div>

        <h2 className="text-xl font-bold">
          Board <span>- Help-Desk Ticket</span>
        </h2>
      </div>
      <SideBar
        isSideBarOpen={isSideBarOpen}
        setIsSideBarOpen={setIsSideBarOpen}
        setVisibleStyled={setVisibleStyled}
      />
    </nav>
  );
}

export default Navbar;
