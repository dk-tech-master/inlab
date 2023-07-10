/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./index.html", "./src/**/*.{vue,js,ts,jsx,tsx}"],
  theme: {
    extend: {
      colors: {
        // indigo 700 "#4338ca"
        // indigo 600 "#4fs6e5"
        primary: "#4338ca",
        secondary: "#4fs6e5",
      },
    },
  },
  plugins: [require("daisyui")],
};
