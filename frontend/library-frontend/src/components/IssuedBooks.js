import { useEffect, useState } from "react";
import api from "../api/axios";

function IssuedBooks() {
  const [books, setBooks] = useState([]);

  useEffect(() => {
    loadIssuedBooks();
  }, []);

  const loadIssuedBooks = async () => {
    const res = await api.get("/student/my-books");
    setBooks(res.data);
  };

  const returnBook = async (bookId) => {
    const res = await api.post(`/student/return/${bookId}`);

    alert("Book returned");

    loadIssuedBooks();
  };

  return (
    console.log(books) || (
      <div>
        {books.map((book) => (
          <div key={book.bookTitle}>
            <h4>{book.bookTitle}</h4>

            <p>Issue Date: {book.issueDate}</p>

            <p>Due Date: {book.dueDate}</p>

            <p>Status: {book.status}</p>

            <p>Fine: ₹{book.fine}</p>

            {book.status === "ISSUED" && (
              <button onClick={() => returnBook(book.bookId)}>
                Return Book
              </button>
            )}
          </div>
        ))}
      </div>
    )
  );
}

export default IssuedBooks;
