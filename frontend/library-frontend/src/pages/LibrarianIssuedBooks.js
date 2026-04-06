import { useEffect, useState } from "react";
import api from "../api/axios";

function LibrarianIssuedBooks() {
  const [issuedBooks, setIssuedBooks] = useState([]);

  useEffect(() => {
    loadIssuedBooks();
  }, []);

  const loadIssuedBooks = async () => {
    const res = await api.get("/librarian/issued-books");

    setIssuedBooks(res.data);
  };

  return (
    <div className="p-10">
      <h2 className="text-2xl font-bold mb-6">Issued Books</h2>

      <table className="w-full border">
        <thead className="bg-gray-200">
          <tr>
            <th className="p-2 border">Student</th>
            <th className="p-2 border">Book</th>
            <th className="p-2 border">Issue Date</th>
            <th className="p-2 border">Due Date</th>
            <th className="p-2 border">Status</th>
          </tr>
        </thead>

        <tbody>
          {issuedBooks.map((book) => (
            <tr key={book.bookId}>
              <td className="border p-2">{book.studentName}</td>

              <td className="border p-2">{book.bookTitle}</td>

              <td className="border p-2">{book.issueDate}</td>

              <td className="border p-2">{book.dueDate}</td>

              <td className="border p-2">{book.status}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default LibrarianIssuedBooks;
