<template>
    <div class="screen">
        <div class="map_screen">
            <img id="mapimg" src="../assets/map_201.jpeg"/>
        </div>
    </div>
</template>

<script setup>
document.addEventListener("DOMContentLoaded", function(){
    var mapImage = document.getElementById("mapimg");
    var offsetX = 0;
    var offsetY = 0;
    var dragging = false;

    mapImage.addEventListener("mousedown", function(e) {
        dragging = true;
        offsetX = e.clientX - mapImage.offsetLeft;
        offsetY = e.clientY - mapImage.offsetTop;
    })

    document.addEventListener("mousemove", function(e) {
        if(dragging){
            e.preventDefault;
            var x = e.clientX - offsetX.value;
            var y = e.clientY - offsetY.value;
            mapImage.style.left = x + "px";
            mapImage.style.top = y + "px";
        }
    })

    document.addEventListener("mouseup", function(){
        dragging = false;
    })
})
</script>

<style>
@import "../style/wmistyle.css";
</style>