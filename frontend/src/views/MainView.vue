<template>
    <div class = "screen">
        <div class = "inner_screen">
            <div class = "inner_header">
                <img class = "profile" v-bind:src = "image.src" v-bind:alt = "image.alt"/>
                <p style="color: white;"><a v-bind:href = "url">{{userName}}</a>님 반갑습니다!</p>
                <div style = "width: 120px;"></div>
                <button class = "gear"></button>
            </div>
            <p style = "position: absolute; color: white; left: 27px; top: 130px; font-size: 30px; font-weight: bold; font-family: serif;">Where am I ?</p>
            <p style = "position: absolute; color: white; left: 27px; top: 181px;">주변 기기를 당신의 마음대로 편하게 제어하세요<br>
                시간은 걱정마세요, 빠른 길로 안내합니다.</p>
        </div>
        <div style="width: 389px; height: 800px; position: relative; background-color: white;">
            <button class = "big_btn">지도</button>
            <button class = "small_btn" style = "left: 24px;">
                전체<br> 
                리소스
            </button>
            <button class = "small_btn" style = "left: 201px;">
                나의<br>
                리소스
            </button>
        </div>
    </div>
</template>

<script>
export default{
    data: () => ({
            userName : "김계륵",
            dynamicId : 'profile',
            url : 'https://www.naver.com',
            image: {
                src: 'https://placeimg.com/100/100/any',
                alt: 'profile image'
            }
        })
}
</script>

<style>
@import "../style/wmistyle.css";
</style>