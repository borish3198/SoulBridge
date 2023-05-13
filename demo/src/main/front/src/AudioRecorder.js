import React, { useState } from "react";
import './AudioRecorder.css'; // Import your CSS file
import { useNavigate } from "react-router-dom";

function AudioRecorder() {
  const [recording, setRecording] = useState(false);
  const [audioURL, setAudioURL] = useState("");
  const [audioData, setAudioData] = useState();
  const navigate = useNavigate(); // useHistory hook for programmatically navigating

  const startRecording = async () => {
    const stream = await navigator.mediaDevices.getUserMedia({ audio: true });
    const mediaRecorder = new MediaRecorder(stream);
    const audioChunks = [];

    mediaRecorder.addEventListener("dataavailable", event => {
      audioChunks.push(event.data);
    });

    mediaRecorder.addEventListener("stop", () => {
      const audioBlob = new Blob(audioChunks, { 'type' : 'audio/webm' });
      const audioUrl = URL.createObjectURL(audioBlob);
      const formData = new FormData();

      formData.append("audio", audioBlob, "audio.webm");
      setAudioData(formData);
      setAudioURL(audioUrl);
    });

    mediaRecorder.start();

    setRecording(mediaRecorder);
  };

  const stopRecording = () => {
    recording.stop();
    setRecording(false);
  };

  const toggleRecording = () => {
    if (recording) {
      stopRecording();
    } else {
      startRecording();
    }
  };

  const sendAudio = async () => {
    if (audioData) {
      // This example assumes that you have a server endpoint at /upload that accepts POST requests.
      const response = await fetch('/upload', {
        method: 'POST',
        body: audioData,  // Pass the FormData instance as the request body
      });
  
      if (response.status === 200)
      {
        navigate('/result', { state: response.status }); // Update this line
      }
      if (!response.ok) {
        console.error('Upload failed');
      }
    }
  };

  // function ResultPage({ location }) {
  //   const result = location.state.result;
  
  //   // ... 
  // }

  return (
    <div className="container">
      <h1>SoulBridge</h1>
      <img src="https://thumbs.dreamstime.com/b/vector-illustration-praying-hands-inspired-albrecht-durer-s-study-praying-hands-sticker-black-142411028.jpg" alt="Your Description" className="logo"/>
      <button className="record-btn" onClick={toggleRecording}>
        {recording ? "Stop Recording" : "Start Recording"}
      </button>
      <button className="send-btn" onClick={sendAudio} disabled={!audioData}>
        Send Audio
      </button>
      {audioURL && <audio src={audioURL} controls />}
    </div>
  );
}

export default AudioRecorder;
