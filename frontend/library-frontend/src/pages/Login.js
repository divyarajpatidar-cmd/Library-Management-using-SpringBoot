import { useState } from "react";
import api from "../api/axios";
import { useNavigate, Link } from "react-router-dom";

function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      const res = await api.post("/auth/login", {
        username,
        password,
      });
      console.log("Login response:", res.data);
      localStorage.setItem("token", res.data.token);

      if (res.data.role === "ROLE_LIBRARIAN") {
        navigate("/librarian");
      } else {
        navigate("/student");
      }
    } catch (err) {
      console.error(err.response?.data || err.message);
      alert("Invalid credentials");
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-r from-blue-500 via-purple-500 to-indigo-500">
      {/* Glass Card */}

      <div className="backdrop-blur-lg bg-white/20 border border-white/30 shadow-xl rounded-xl p-10 w-96">
        <h2 className="text-3xl font-bold text-white text-center mb-8">
          Library Login
        </h2>

        <input
          className="w-full p-3 mb-4 rounded-lg bg-white/40 placeholder-white text-white focus:outline-none"
          placeholder="Username"
          onChange={(e) => setUsername(e.target.value)}
        />

        <input
          type="password"
          className="w-full p-3 mb-6 rounded-lg bg-white/40 placeholder-white text-white focus:outline-none"
          placeholder="Password"
          onChange={(e) => setPassword(e.target.value)}
        />

        <button
          onClick={handleLogin}
          className="w-full bg-white text-blue-600 font-semibold py-3 rounded-lg hover:bg-gray-200 transition"
        >
          Login
        </button>

        <p className="text-center text-white mt-6 text-sm">
          Don't have an account?
          <Link to="/register" className="ml-1 underline">
            Register
          </Link>
        </p>
      </div>
    </div>
  );
}

export default Login;
