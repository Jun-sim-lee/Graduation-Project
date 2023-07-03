<template>
    <div class = "screen">
        <div class = "resource_header">
            <div class = "inner_header">
                <button @click="moveToPrev" style="line-height: 50px; color: white; background-color: transparent; border: none; font-size: 20px"> &lt; 전체 리소스</button>
                <button class = "gear"></button>
            </div>
            <p style = "position: absolute; color: white; left: 27px; top: 40px; font-size: 30px; font-weight: bold; font-family: serif;">All Resource</p>
            <p style = "position: absolute; color: white; left: 27px; top: 100px;">모든 리소스 목록입니다.<br>
                정렬을 통해 권한 별 리소스 확인이 가능합니다.</p>
        </div>
        <div style="width: 389px; height: 611px; position: relative; background-color: white;">
            <ul>
            </ul>
        </div>
    </div>
</template>

<script setup>
import { router } from '@/router';
function moveToPrev(){
    router.go(-1)
}
</script>

<style>
@import "../style/wmistyle.css";
</style>