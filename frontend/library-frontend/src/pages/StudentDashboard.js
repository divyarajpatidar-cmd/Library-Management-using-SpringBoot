import { useEffect, useState } from "react";
import api from "../api/axios";

function StudentDashboard() {

  const [books,setBooks] = useState([]);
  const [myBooks,setMyBooks] = useState([]);

  useEffect(()=>{
    loadBooks();
    loadMyBooks();
  },[]);

  const loadBooks = async ()=>{
    const res = await api.get("/student/books");
    setBooks(res.data);
  };

  const loadMyBooks = async ()=>{
    const res = await api.get("/student/my-books");
    setMyBooks(res.data);
  };

  const issueBook = async (bookId)=>{
    await api.post(`/student/issue/${bookId}`);
    loadBooks();
    loadMyBooks();
  };

  const returnBook = async (bookId)=>{
    await api.post(`/student/return/${bookId}`);
    loadBooks();
    loadMyBooks();
  };

  return(

    <div className="min-h-screen bg-gray-100 p-10">

      <h2 className="text-3xl font-bold mb-8 text-gray-800">
        Student Dashboard
      </h2>

      {/* Available Books */}

      <h3 className="text-xl font-semibold mb-4">Available Books</h3>

      <div className="grid grid-cols-3 gap-6 mb-10">

        {books.map(book=>(
          <div
            key={book.id}
            className="bg-white shadow-md rounded-lg p-5 hover:shadow-xl transition"
          >

            <h4 className="font-semibold text-lg">
              {book.title}
            </h4>

            <p className="text-gray-600 mt-1">
              Author: {book.author}
            </p>

            <p className="text-gray-600">
              Available: {book.availableQuantity}
            </p>

            <button
              onClick={()=>issueBook(book.id)}
              className="mt-3 bg-blue-600 text-white px-4 py-1 rounded hover:bg-blue-700"
            >
              Issue
            </button>

          </div>
        ))}

      </div>

      {/* My Books */}

      <h3 className="text-xl font-semibold mb-4">My Books</h3>

      <div className="bg-white shadow rounded-lg overflow-hidden">

        <table className="w-full">

          <thead className="bg-gray-200">

            <tr>
              <th className="p-3">Book</th>
              <th className="p-3">Issue Date</th>
              <th className="p-3">Due Date</th>
              <th className="p-3">Status</th>
              <th className="p-3">Action</th>
            </tr>

          </thead>

          <tbody>

            {myBooks.map((b,index)=>(
              <tr key={index} className="border-t text-center">

                <td className="p-3">{b.bookTitle}</td>
                <td className="p-3">{b.issueDate}</td>
                <td className="p-3">{b.dueDate}</td>
                <td className="p-3">{b.status}</td>

                <td>
                  {b.status === "ISSUED" && (
                    <button
                      onClick={()=>returnBook(b.bookId)}
                      className="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600"
                    >
                      Return
                    </button>
                  )}
                </td>

              </tr>
            ))}

          </tbody>

        </table>

      </div>

    </div>
  );
}

export default StudentDashboard;