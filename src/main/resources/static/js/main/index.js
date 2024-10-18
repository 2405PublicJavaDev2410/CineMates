document.addEventListener('DOMContentLoaded', function() {
    const videos = [
        "https://cf.lottecinema.co.kr//Media/MovieFile/MovieMedia/202410/21626_301_1.mp4",
        "https://cf.lottecinema.co.kr//Media/MovieFile/MovieMedia/202409/21394_301_1.mp4",
        "https://cf2.lottecinema.co.kr/lotte_image/2024/TheKillers/TheKillers_1280720.mp4",
        "https://cf2.lottecinema.co.kr/lotte_image/2024/Audrey/Audrey_1280720.mp4",
        "https://cf2.lottecinema.co.kr/lotte_image/2024/TwilightOfTheWarriors/TwilightOfTheWarriors_1280720.mp4"
    ];

    const randomVideo = videos[Math.floor(Math.random() * videos.length)];
    const videoElement = document.getElementById('randomVideo');
    const sourceElement = document.createElement('source');

    sourceElement.src = randomVideo;
    sourceElement.type = 'video/mp4';

    videoElement.appendChild(sourceElement);
});


