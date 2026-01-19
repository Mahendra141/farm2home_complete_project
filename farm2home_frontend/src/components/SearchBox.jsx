import { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import styles from "./Navbar.module.css";
import { Search } from "lucide-react";

export default function SearchBox() {
  const [query, setQuery] = useState("");
  const [suggestions, setSuggestions] = useState([]);
  const navigate = useNavigate();
  const boxRef = useRef(null);

  /* 🔁 Debounced API call */
  useEffect(() => {
    if (!query.trim()) {
      setSuggestions([]);
      return;
    }

    const timer = setTimeout(() => {
      axios
        .get(
          `http://localhost:8080/api/products/suggestions?q=${query}`
        )
        .then((res) => setSuggestions(res.data))
        .catch(() => setSuggestions([]));
    }, 300);

    return () => clearTimeout(timer);
  }, [query]);

  /* ❌ Close dropdown on outside click */
  useEffect(() => {
    const handleClickOutside = (e) => {
      if (boxRef.current && !boxRef.current.contains(e.target)) {
        setSuggestions([]);
      }
    };

    document.addEventListener("click", handleClickOutside);
    return () =>
      document.removeEventListener("click", handleClickOutside);
  }, []);

  const handleSelect = (value) => {
    setQuery("");
    setSuggestions([]);
    navigate(`/search?q=${value}`);
  };

  return (
    <div
      className={styles.searchContainer}
      ref={boxRef}
      onClick={(e) => e.stopPropagation()}
    >
      {/* INPUT */}
      <div className={styles.searchWrapper}>
        <Search size={18} className={styles.icon} />
        <input
          className={styles.input}
          type="text"
          placeholder="Search vegetables, fruits..."
          value={query}
          autoComplete="off"   // 🔥 IMPORTANT
          spellCheck="false"   // 🔥 IMPORTANT
          onChange={(e) => setQuery(e.target.value)}
        />
      </div>

      {/* SUGGESTIONS */}
      {suggestions.length > 0 && (
        <ul className={styles.suggestions}>
          {suggestions.map((item) => (
            <li
              key={item}
              onClick={() => handleSelect(item)}
            >
              {item}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}
