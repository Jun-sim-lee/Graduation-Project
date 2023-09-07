<template>
    <div class = "screen">
        <div class = "resource_header">
            <div class = "inner_header">
                <button @click="moveToPrev" style="line-height: 50px; color: white; background-color: transparent; border: none; font-size: 20px"> &lt; 목적지 설정</button>
            </div>
            <p style = "position: absolute; color: white; left: 27px; top: 40px; font-size: 30px; font-weight: bold; font-family: serif;">Where to go?</p>
            <p style = "position: absolute; color: white; left: 27px; top: 100px;">지정할 수 있는 목적지 목록입니다.<br>
                선택된 목적지까지 최단경로를 안내합니다.</p>
        </div>
        <div style="width: 389px; height: 611px; position: relative; background-color: white;">
            <ul>
                <li class = "resource_list" style = "text-align: center;" 
                    :key="destination.name" v-for="destination in destinationList">
                    <span style="font-size: 18px; line-height: 30px;">{{ destination.name }}</span>
                </li>
            </ul>
        </div>
    </div>
</template>

<script setup>
import { router } from '@/router';
import {ref, inject, onMounted} from 'vue'
import { useStore } from 'vuex';
import axios from 'axios';

const store = useStore();
const headers = JSON.parse(inject('headers') + store.state.accessToken + '"}');
const requestURL = inject('requestURL')

const destinationList = ref([
    {name: "6208", coord_x: 8, coord_y: 10},
    {name: "남자화장실", coord_x: 4, coord_y: 8},
    {name: "여자화장실", coord_x: 8, coord_y: 8},
    {name: "계단", coord_x: 10, coord_y: 10}
])

function requestDestinationList(){
    axios.get(requestURL + "resource", {headers})
        .then((resp) => {
            destinationList.value = resp.data;
        })
}

onMounted(() => {
    requestDestinationList();
})

function moveToPrev(){
    router.go(-1)
}
</script>

<style>
@import "../style/wmistyle.css";
</style>