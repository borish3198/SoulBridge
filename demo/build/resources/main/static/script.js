// script.js 파일
document.getElementById('uploadBtn').addEventListener('click', () => {
    const audioFileInput = document.getElementById('audioFile');
    const audioFile = audioFileInput.files[0];

    if (!audioFile) {
        alert('음원 파일을 선택해주세요.');
        return;
    }

    // 서버로 음원 파일을 업로드하는 함수를 호출합니다. (아래에 구현해야 함)
    uploadAudioFile(audioFile);
});

function uploadAudioFile(file) {
    // 여기에서 서버로 파일을 업로드하는 로직을 구현해야 합니다.
    // 예를 들어, FormData를 사용하여 파일을 멀티파트 요청으로 전송할 수 있습니다.
    // 다음은 예시입니다.

    const formData = new FormData();
    formData.append('audio', file);

    fetch('/upload', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (response.ok) {
            alert('음원 파일이 업로드되었습니다.');
            window.location.href = '/result?filename=' + encodeURIComponent(file.name);
        } else {
            alert('음원 파일 업로드에 실패했습니다.');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('음원 파일 업로드 중 오류가 발생했습니다.');
    });
}
