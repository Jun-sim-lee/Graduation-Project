<template>
    <div class="screen" @mouseup="stopDrag($event)">
        <button @click="toSelectDestionation" style="position: absolute; z-index: 3; top: 20px; opacity: 50%; color: black;
                       width: 270px; height: 40px; border-radius: 5px; border: none;">목적지를 선택해주세요!</button>
        <div class="map_screen">
            <div @mousedown="startDrag($event)" @mousemove="moveDrag($event)"
                 class="map_div">
                <div v-bind:style="{position: 'absolute', zIndex: 2, left: `${coordDto.client_x}px`, top: `${coordDto.client_y}px`, 
                                backgroundColor: 'red', pointerEvents: 'none',
                                border: '5px solid aliceblue', borderRadius: '100%', width: '10px', height: '10px'}"
                     id = "client_dot"></div>
            </div>
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
const coordDto = ref({
    client_x: 1240,
    client_y: 450
    // 1320에 510이 0, 7과 같다.
    // 1320, 450이 0, 8이니 y축으로는 한 칸에 60 정도
    // 1240, 450이 1, 8이니 x축으로는 한 칸에 80 정도
    // 이제, 받아오는 좌표에 따라 점화식을 만들어 계산할 수 있어야 함
})

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