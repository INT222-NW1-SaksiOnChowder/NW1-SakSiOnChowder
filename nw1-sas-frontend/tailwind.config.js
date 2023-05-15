/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
    },
    colors: {
      "Background" : "#E4E5E9",
      "LightBlue" : "#D1D8DE",
      "DarkBlue" : "#A0B1C1",
      "DarkGreen" : "#999B86",
      "DarkRed" : "#BD6666",
      "BlueFonts" : "#1C3150",
      "Cream" : "#F0F0F0",
      "ButtonViewHover" : "#C7C0B6",
      "ButtonDeleteHover" : "#DCACAC"
    }
  },
  plugins: [require("daisyui")],
}