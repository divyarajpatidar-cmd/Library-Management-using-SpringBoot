import { Link } from "react-router-dom";

function Navbar() {

  const logout = () => {
    localStorage.removeItem("token");
    window.location.href = "/login";
  };

  return (
    <div className="bg-slate-900 text-white px-8 py-4 flex justify-between items-center shadow-lg">

      <h1 className="text-2xl font-bold tracking-wide">
        Library System
      </h1>

      <div className="space-x-6 text-sm">

        <Link
          to="/student"
          className="hover:text-gray-300"
        >
          Student
        </Link>

        <Link
          to="/librarian"
          className="hover:text-gray-300"
        >
          Librarian
        </Link>

        <button
          onClick={logout}
          className="bg-red-500 px-4 py-1 rounded hover:bg-red-600"
        >
          Logout
        </button>

      </div>

    </div>
  );
}

export default Navbar;