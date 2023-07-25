<template>
    <div class="screen" @touchend="stopDrag($event)">
        <button @click="toSelectDestionation" style="position: absolute; z-index: 1; top: 20px; opacity: 50%; color: black;
                       width: 270px; height: 40px; border-radius: 5px; border: none;">목적지를 선택해주세요!</button>
        <div class="map_screen">
            <img id="csemap" src="../assets/map_201.jpeg"
                 style="position: absolute; left: 0px; top: 0px;"
                 @touchstart="startDrag($event)" @touchmove="moveDrag($event)"/>
        </div>
    </div>
</template>

<script setup>
import {ref} from 'vue'
import { router } from '@/router';

var imgLeft = 0
var imgTop = 0
var moveLeft = 0
var moveTop = 0
const isClick = ref(false)

function startDrag(event){
    imgLeft = event.pageX
    imgTop = event.pageY
    isClick.value = true

    if(event.preventDefault)
        event.preventDefault();
}

function moveDrag(event){
    if(isClick.value){
        moveLeft = event.pageX
        moveTop = event.pageY
        if(parseInt(event.target.style.left.replace('px', '')) >= -1000 && parseInt(event.target.style.left.replace('px', '')) <= 0)
            event.target.style.left = (parseInt(event.target.style.left.replace('px', '')) - ((imgLeft - moveLeft))/15) + "px";
        else if(parseInt(event.target.style.left.replace('px', '')) < -1000)
            event.target.style.left = -999 + "px";
        else
            event.target.style.left = 0+ "px";
        if(parseInt(event.target.style.top.replace('px', '')) >= -200 && parseInt(event.target.style.top.replace('px', '')) <= 0)
            event.target.style.top = (parseInt(event.target.style.top.replace('px', '')) - ((imgTop - moveTop))/15) + "px";
        else if(parseInt(event.target.style.top.replace('px', '')) < -200)
            event.target.style.top =  -199 + "px";
        else
            event.target.style.top = 0 + "px";

        console.log(event.target.style.left + " " + event.target.style.top)
        return false
    }
}

function stopDrag(){
    isClick.value = false

    imgLeft = moveLeft
    imgTop = moveTop
}

function toSelectDestionation(){
    router.push('destination')
}

</script>

<style>
@import "../style/wmistyle.css";
</style>