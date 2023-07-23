<template>
    <div class="screen">
        <div class="map_screen">
            <img id="csemap" src="../assets/map_201.jpeg"
                 style="position: absolute; left: 0px; top: 0px;"
                 @mousedown="startDrag($event)" @mouseup="stopDrag($event)" @click="moveDrag($event)"/>
        </div>
    </div>
</template>

<script setup>
import {ref} from 'vue'
var imgLeft = 0
var imgTop = 0

function startDrag(event){
    const csemap = ref(event.target);
    imgLeft = parseInt(csemap.value.style.left.replace('px', '')) - event.clientX
    imgTop = parseInt(csemap.value.style.top.replace('px', '')) - event.clientY

    if(event.preventDefault)
        event.preventDefault();
}

function moveDrag(event){
    var dmvx = parseInt(event.clientX + imgLeft);
    var dmvy = parseInt(event.clientY + imgTop);

    event.target.style.left = dmvx + "px";
    event.target.style.top = dmvy + "px";
    return false
}

function stopDrag(event){
    event.target.onmousemove = null
    event.target.onmouseup = null
}

</script>

<style>
@import "../style/wmistyle.css";
</style>