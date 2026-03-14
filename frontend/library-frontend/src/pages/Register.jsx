import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axios";

function Register() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [fullName, setFullName] = useState("");
  const [role, setRole] = useState("STUDENT");
  const navigate = useNavigate();

  const handleRegister = async () => {
    try {
      const res = await api.post("/auth/register", {
        username,
        password,
        fullName,
        role,
      });

      if (res.data.token) {
        localStorage.setItem("token", res.data.token);

        const payload = JSON.parse(atob(res.data.token.split(".")[1]));
        console.log("registration response:", res.data);
        console.log("DECODED TOKEN PAYLOAD:", payload);
        const roles = payload.roles || [];

        if (res.data.role === "ROLE_LIBRARIAN") {
          navigate("/librarian");
        } else {
          navigate("/student");
        }
      } else {
        alert("Registered successfully");

        if (role === "LIBRARIAN") {
          navigate("/librarian");
        } else {
          navigate("/student");
        }
      }
    } catch (error) {
      console.error(error);
      alert("Registration failed!");
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-r from-blue-500 via-purple-500 to-indigo-600">
      <div className="backdrop-blur-lg bg-white/20 border border-white/30 shadow-xl rounded-2xl p-8 w-96">
        <h2 className="text-3xl font-bold text-white text-center mb-6">
          Create Account
        </h2>

        <input
          className="w-full p-3 mb-4 rounded-lg bg-white/80 focus:outline-none focus:ring-2 focus:ring-blue-400"
          placeholder="Full Name"
          value={fullName}
          onChange={(e) => setFullName(e.target.value)}
        />

        <input
          className="w-full p-3 mb-4 rounded-lg bg-white/80 focus:outline-none focus:ring-2 focus:ring-blue-400"
          placeholder="Username / Email"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />

        <input
          type="password"
          className="w-full p-3 mb-4 rounded-lg bg-white/80 focus:outline-none focus:ring-2 focus:ring-blue-400"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <select
          className="w-full p-3 mb-6 rounded-lg bg-white/80 focus:outline-none focus:ring-2 focus:ring-blue-400"
          value={role}
          onChange={(e) => setRole(e.target.value)}
        >
          <option value="STUDENT">Student</option>
          <option value="LIBRARIAN">Librarian</option>
        </select>

        <button
          onClick={handleRegister}
          className="w-full bg-blue-600 text-white py-3 rounded-lg font-semibold hover:bg-blue-700 transition duration-200"
        >
          Register
        </button>

        <p className="text-center text-white mt-5 text-sm">
          Already have an account?{" "}
          <span
            className="font-semibold underline cursor-pointer"
            onClick={() => navigate("/")}
          >
            Login
          </span>
        </p>
      </div>
    </div>
  );
}

export default Register;
