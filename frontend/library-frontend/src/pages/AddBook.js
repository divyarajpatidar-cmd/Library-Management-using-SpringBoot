import { useState } from "react";
import api from "../api/axios";

function AddBook() {
  const [title, setTitle] = useState("");
  const [author, setAuthor] = useState("");
  const [isbn, setIsbn] = useState("");
  const [totalQuantity, setTotalQuantity] = useState("");

  const addBook = async () => {
    await api.post("/librarian/books", {
      title,
      author,
      isbn,
      totalQuantity,
    });

    alert("Book added successfully");
  };

  return (
    <div className="flex justify-center items-center min-h-screen bg-gray-100">
      <div className="bg-white p-8 shadow-lg rounded-lg w-96">
        <h2 className="text-xl font-bold mb-6 text-center">Add Book</h2>

        <input
          className="border w-full p-2 mb-4 rounded"
          placeholder="Title"
          onChange={(e) => setTitle(e.target.value)}
        />

        <input
          className="border w-full p-2 mb-4 rounded"
          placeholder="Author"
          onChange={(e) => setAuthor(e.target.value)}
        />

        <input
          className="border w-full p-2 mb-4 rounded"
          placeholder="ISBN"
          onChange={(e) => setIsbn(e.target.value)}
        />

        <input
          type="number"
          className="border w-full p-2 mb-4 rounded"
          placeholder="Total Quantity"
          onChange={(e) => setTotalQuantity(e.target.value)}
        />

        <button
          onClick={addBook}
          className="bg-blue-600 text-white w-full py-2 rounded hover:bg-blue-700"
        >
          Add Book
        </button>
      </div>
    </div>
  );
}

export default AddBook;
