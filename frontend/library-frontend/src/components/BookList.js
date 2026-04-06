import { useEffect, useState } from "react";
import api from "../api/axios";

function BookList() {
  const [books, setBooks] = useState([]);

  useEffect(() => {
    loadBooks();
  }, []);

  const loadBooks = async () => {
    const res = await api.get("/student/books");
    setBooks(res.data);
  };

  const issueBook = async (bookId) => {
    await api.post(`/student/issue/${bookId}`);
    alert("Book issued successfully");
  };

  return (
    <div>
      {books.map((book) => (
        <div key={book.id}>
          <h4>{book.title}</h4>
          <p>{book.author}</p>

          <button onClick={() => issueBook(book.id)}>Issue Book</button>
        </div>
      ))}
    </div>
  );
}

export default BookList;
