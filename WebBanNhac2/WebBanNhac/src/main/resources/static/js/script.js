function imgError(image) {
                    image.onerror = "";
                    image.src = "~/Hinh/Default.jpg";
                    return true;
                }

                function checkImageSrc() {
                    var image = document.getElementById("song-img");
                    if (image.src === "") {
                        image.src = "~/Hinh/Default.jpg";
                    }
                }

                window.onload = checkImageSrc;

                var index = 0; // Lưu trữ chỉ mục của bài hát đang được phát


                var currentCoverImage = '~/Hinh/Default.jpg';

                function changeSongTitle(title) {
                    var songTitles = document.querySelectorAll(".titlesong");
                    for (var i = 0; i < songTitles.length; i++) {
                        songTitles[i].innerText = title.substring(0, title.length - 4);
                    }
                }
                function changeSongArtist(artist) {
                    var songArtist = document.querySelectorAll(".artist");
                    for (var i = 0; i < songTitles.length; i++) {
                        songArtist[i].innerText = artist;
                    }
                }


                function playSong(linkbaihat, hinhbaihat, Tenbaihat, casi) {
                    var audio = document.getElementById("song");
                    audio.src = linkbaihat;
                    audio.load();
                    audio.play();
                    var songImgs = document.querySelectorAll("#song-img");
                    for (var i = 0; i < songImgs.length; i++) {
                        songImgs[i].onerror = function () {
                            this.src = currentCoverImage;
                        };
                        songImgs[i].src = hinhbaihat;
                    }
                    var songTitles = document.querySelectorAll(".titlesong");
                    for (var i = 0; i < songTitles.length; i++) {
                        songTitles[i].innerText = Tenbaihat;
                    }
                    var songArtist = document.querySelectorAll(".artist");
                    for (var i = 0; i < songArtist.length; i++) {
                        songArtist[i].innerText = casi;
                    }
                    changeSongTitle(Tenbaihat);
                    changeSongArtist(casi)
                    currentCoverImage = hinhbaihat;
                }

                function playSongAndChangeImg(linkbaihat, hinhbaihat, Tenbaihat) {
                    var audio = document.getElementById("song");
                    audio.src = linkbaihat;
                    audio.load();
                    audio.play();
                    var songImgs = document.querySelectorAll("#song-img");
                    for (var i = 0; i < songImgs.length; i++) {
                        songImgs[i].src = hinhbaihat;
                    }
                    changeSongTitle(Tenbaihat); // Đổi tên bài hát
                }

                function skipSong() {
                    var currentSongImg = document.getElementById("song-img");

                    // Cập nhật hình ảnh của phần tử hiện tại
                    currentSongImg.src = currentCoverImage;

                    var songs = document.getElementsByClassName("song-item");
                    index++;
                    if (index >= songs.length) {
                        index = 0;
                    }
                    var linkbaihat = songs[index].getAttribute("data-link");
                    var coverImage = songs[index].getAttribute("data-coverimage");
                    var Name = songs[index].getAttribute("data-name");
                    var artist = songs[index].getAttribute("data-artist");
                    playSong(linkbaihat, coverImage, Name, artist);
                    updateSongInfo(songs[index]);

                    // Lưu trữ phần tử hiện tại vào biến
                }

                function prevSong() {
                    var songs = document.getElementsByClassName("song-item");
                    index--;
                    if (index < 0) {
                        index = songs.length - 1;
                    }
                    var linkbaihat = songs[index].getAttribute("data-link");
                    var coverImage = songs[index].getAttribute("data-coverimage");
                    var Name = songs[index].getAttribute("data-name");
                    var artist = songs[index].getAttribute("data-artist");
                    playSong(linkbaihat, coverImage, Name, artist);
                    updateSongInfo(songs[index]);
                    document.getElementById("song-img").src = currentCoverImage;

                }
                var isShuffleOn = false;

                function shufflePlaylist() {
                    var songs = document.getElementsByClassName("song-item");
                    var currentIndex = index;
                    var randomIndex = Math.floor(Math.random() * songs.length);
                    while (randomIndex === currentIndex) {
                        randomIndex = Math.floor(Math.random() * songs.length);
                    }
                    index = randomIndex;
                    var linkbaihat = songs[index].getAttribute("data-link");
                    var coverImage = songs[index].getAttribute("data-coverimage");
                    var Name = songs[index].getAttribute("data-name");
                    var artist = songs[index].getAttribute("data-artist");
                    playSong(linkbaihat, coverImage, Name, artist);

                }

                function toggleShuffle() {
                    var shuffleBtn = document.querySelector(".shuffle");
                    shuffleBtn.classList.toggle("active");
                    if (isShuffleOn) {
                        isShuffleOn = false;
                    } else {
                        isShuffleOn = true;
                    }
                }


                function repeatSong() {
                    var repeatBtn = document.querySelector(".repeat");
                    repeatBtn.classList.toggle("active");
                    var audio = document.getElementById("song");
                    if (audio.loop) {
                        audio.loop = false;
                        document.querySelector('.repeat-button').classList.remove('active');

                    } else {
                        audio.loop = true;
                        document.querySelector('.repeat-button').classList.add('active');

                    }
                }
                function search() {
                    var keyword = document.getElementById("search-input").value;
                    window.location.href = "/Home/Search?keyword=" + keyword;
                }
                let progress = document.getElementById("progress");
                        let song = document.getElementById("song");
                        let ctrlIcon = document.getElementById("ctrlIcon");
                        song.onloadedmetadata = function () {
                            progress.max = song.duration;
                            progress.value = song.currentTime;
        }
        function playPause() {
            if (song.paused || song.ended) {
                song.play();
                ctrlIcon.classList.remove("fa-play");
                ctrlIcon.classList.add("fa-pause");
            }
            else {
                song.pause();
                ctrlIcon.classList.remove("fa-pause");
                ctrlIcon.classList.add("fa-play");
            }
        }
        song.onloadeddata = function () {
            if (!song.paused && !song.ended) {
                ctrlIcon.classList.remove("fa-play");
                ctrlIcon.classList.add("fa-pause");
            }
        };

        if (song.play()) {
            setInterval(() => {
                progress.value = song.currentTime;
            }, 500)
        }
        progress.onchange = function () {
            song.play();
            song.currentTime = progress.value;
            ctrlIcon.classList.remove("fa-play");
            ctrlIcon.classList.add("fa-pause");
        }
        let currentTime = document.querySelector('.current-time');
        let totalDuration = document.querySelector('.total-duration');

        song.addEventListener('timeupdate', function () {
            let current = Math.floor(song.currentTime);
            let duration = Math.floor(song.duration);
            currentTime.textContent = formatTime(current);
            totalDuration.textContent = formatTime(duration);
        });

        function formatTime(seconds) {
            let min = Math.floor(seconds / 60);
            let sec = seconds % 60;
            return (min < 10 ? '0' : '') + min + ':' + (sec < 10 ? '0' : '') + sec;
        }
        let volumeControl = document.getElementById("volume");

        volumeControl.oninput = function () {
            song.volume = this.value / 100;
            let volumeIcon = document.querySelector(".pcontrols i");
            if (this.value == 0) {
                volumeIcon.classList.remove("fa-volume-high", "fa-volume-low");
                volumeIcon.classList.add("fa-volume-xmark");
            } else if (this.value < 50) {
                volumeIcon.classList.remove("fa-volume-high", "fa-volume-xmark");
                volumeIcon.classList.add("fa-volume-low");
            } else {
                volumeIcon.classList.remove("fa-volume-low", "fa-volume-xmark");
                volumeIcon.classList.add("fa-volume-high");
            }
        };
        let currentVolume = 100;
        let volumeIcon = document.querySelector(".pcontrols i");



        function toggleMute() {
            if (song.volume == 0) {
                // Unmute and set volume to previous value
                song.volume = currentVolume / 100;
                volumeControl.value = currentVolume;
                volumeIcon.classList.remove("fa-volume-xmark");
                volumeIcon.classList.add("fa-volume-high");
            } else {
                // Mute and remember volume value
                currentVolume = volumeControl.value;
                song.volume = 0;
                volumeControl.value = 0;
                volumeIcon.classList.remove("fa-volume-high");
                volumeIcon.classList.add("fa-volume-xmark");
            }

        }


        document.body.onkeyup = function (e) {
            if (e.code === 'Space') {
                playPause();
            }
            else if (e.code === 'KeyM') {
                toggleMute();
            }
        };
        song.addEventListener('ended', function () {
            if (isShuffleOn) {
                shufflePlaylist();
            }
            else
            skipSong();

        });

        function changeBackgroundImage(songId) {
                var image = document.getElementById("songimg");
                var imageUrl = "/songs/image/" + songId;
                image.src = imageUrl;
            }
var video = document.getElementById("background-video");
        var btn = document.querySelector(".btn-transition");

        function checkBackgroundColor() {
            var bgColor = window.getComputedStyle(btn).getPropertyValue("background-color");
            if (bgColor === "rgb(255, 255, 255)") {
                playVideo();
            } else {
                pauseVideo();
            }
        }

        function pauseVideo() {
            video.pause();
        }

        function playVideo() {
            video.play();
        }
