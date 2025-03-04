import { useState } from "react";
import Navbar from "../components/Navbar";
import Board from "../components/Board";
import Table from "../components/Table";
function Home() {
  const [visibleStyled, setVisibleStyled] = useState("board"); // "board" or "table"

  return (
    <div className="w-screen h-screen relative ">
      <Navbar setVisibleStyled={setVisibleStyled} />
      {visibleStyled === "board" ? <Board /> : <Table />}
    </div>
  );
}

export default Home;
