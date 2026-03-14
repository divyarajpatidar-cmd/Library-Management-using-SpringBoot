import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";
import StudentDashboard from "./pages/StudentDashboard";
import LibrarianDashboard from "./pages/LibrarianDashboard";
import AddBook from "./pages/AddBook";
import LibrarianIssuedBooks from "./pages/LibrarianIssuedBooks";
import ProtectedRoute from "./routes/ProtectedRoute";

function App() {

  return (
    <BrowserRouter>

      <Routes>

        <Route path="/" element={<Login />} />

        <Route path="/register" element={<Register />} />

        <Route
          path="/student"
          element={
            <ProtectedRoute>
              <StudentDashboard />
            </ProtectedRoute>
          }
        />
  
        <Route
          path="/librarian"
          element={
            <ProtectedRoute>
              <LibrarianDashboard />
            </ProtectedRoute>
          }
        />

        <Route path="/librarian/add-book" element={<ProtectedRoute><AddBook /></ProtectedRoute>} />
        <Route path="/librarian/issued-books" element={<ProtectedRoute><LibrarianIssuedBooks /></ProtectedRoute>} />

      </Routes>

    </BrowserRouter>
  );
}

export default App;