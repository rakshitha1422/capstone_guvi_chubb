/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {
      fontFamily: {
        caveat: ['Caveat', 'sans-serif'],
      },
    },
  },
  plugins: [],
}

