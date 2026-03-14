import { useEffect, useState } from "react";
import api from "../api/axios";
import { useNavigate } from "react-router-dom";

function LibrarianDashboard() {

  const [books, setBooks] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    loadBooks();
  }, []);

  const loadBooks = async () => {

    const res = await api.get("/librarian/books");

    setBooks(res.data);

  };

  const deleteBook = async (id) => {

    await api.delete(`/librarian/books/${id}`);

    loadBooks();

  };

  return (

    <div className="min-h-screen bg-gray-100 p-10">

      <div className="flex justify-between mb-8">

        <h2 className="text-3xl font-bold text-gray-800">
          Librarian Dashboard
        </h2>

        <div className="space-x-3">

          <button
            onClick={() => navigate("/librarian/add-book")}
            className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
          >
            Add Book
          </button>

          <button
            onClick={() => navigate("/librarian/issued-books")}
            className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700"
          >
            Issued Books
          </button>

        </div>

      </div>

      <div className="grid grid-cols-3 gap-6">

        {books.map(book => (

          <div
            key={book.id}
            className="bg-white rounded-lg shadow-md p-6 hover:shadow-xl transition"
          >

            <h3 className="text-lg font-semibold text-gray-800">
              {book.title}
            </h3>

            <p className="text-gray-600 mt-2">
              Author: {book.author}
            </p>

            <p className="text-gray-600">
              Available: {book.availableQuantity}
            </p>

            <button
              onClick={() => deleteBook(book.id)}
              className="mt-4 bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600"
            >
              Delete
            </button>

          </div>

        ))}

      </div>

    </div>
  );
}

export default LibrarianDashboard;