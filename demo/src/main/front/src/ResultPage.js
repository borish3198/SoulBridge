import React, { useEffect, useState } from "react";

function ResultPage() {
  const [result, setResult] = useState('');

  useEffect(() => {
    const fetchResult = async () => {
      const response = await fetch('/result?filename=audio.webm'); // replace with your API endpoint
      if (!response.ok) {
        console.error('Failed to fetch result');
        return;
      }
      const data = await response.text(); // use .json() if your API returns JSON
      setResult(data);
    };

    fetchResult();
  }, []); // empty dependency array so this runs once on mount

  return (
    <div>
      <h1>Result</h1>
      <p>{result}</p>
    </div>
  );
}

export default ResultPage;
