<template>
    <div class="screen" @touchend="stopDrag($event)">
        <div class="map_screen">
            <img id="csemap" src="../assets/map_201.jpeg"
                 style="position: absolute; left: 0px; top: 0px;"
                 @touchstart="startDrag($event)" @touchmove="moveDrag($event)"/>
        </div>
    </div>
</template>

<script setup>
import {ref} from 'vue'
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

        event.target.style.left = (parseInt(event.target.style.left.replace('px', '')) - (imgLeft - moveLeft)) + "px";
        event.target.style.top = (parseInt(event.target.style.top.replace('px', '')) - (imgTop - moveTop)) + "px";
        return false
    }
}

function stopDrag(){
    isClick.value = false

    imgLeft = moveLeft
    imgTop = moveTop
}

</script>

<style>
@import "../style/wmistyle.css";
</style>