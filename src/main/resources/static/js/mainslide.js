function naverGo() {
    const width = 1500;
    const height = 800;
    const left = (window.innerWidth / 2) -(width / 2);
    const top = (window.innerHeight / 2) -(height / 2);
    window.open("https://www.spotify.com/kr-ko/premium/",'_blank', `width=${width}, height=${height},left=${left},top=${top}`);


}