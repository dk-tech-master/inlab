<template>
  <dialog id="createTypeModal" class="modal">
    <form method="dialog" class="modal-box">
      <h2 class="font-bold text-lg mb-6">유형 등록</h2>

      <label
        for="searchInterview"
        class="block text-sm font-bold text-gray-700 mb-2"
        >직무</label
      >
      <select
        class="w-32 h-[3em] bg-gray-50 mb-8 font-medium select select-primary select-sm border-gray-300"
        v-model="selectedPosition"
        ref="positionElement"
      >
        <option disabled selected>직무 선택</option>
        <option
          v-for="(item, index) in props.positionOptions"
          :key="index"
          :value="item.positionId"
        >
          {{ item.positionName }}
        </option>
      </select>
      <label
        for="searchInterview"
        class="block text-sm font-bold text-gray-700 mb-2"
        >유형</label
      >
      <input
        v-model="typeName"
        type="text"
        name="registerJob"
        class="input input-bordered input-primary w-full mr-2 border-gray-300 text-sm"
        placeholder="추가할 유형을 입력해주세요."
        ref="typeNameElement"
        required
      />

      <div class="modal-action">
        <button
          class="flex flex-col mr-3 py-5 px-5 btn btn-sm btn-primary btn-outline"
          type="button"
          @click="toggleModal"
        >
          취소
        </button>
        <button
          type="button"
          class="flex flex-col py-5 px-5 btn btn-sm btn-primary"
          @click="clickCreateTypeBtn"
        >
          확인
        </button>
      </div>
    </form>
  </dialog>
</template>

<script setup>
import { defineEmits, ref } from "vue";
import { createTypes } from "@/api/type";

const emit = defineEmits(["init"]);
const positionElement = ref(null);
const typeNameElement = ref(null);
const selectedPosition = ref();
const typeName = ref();

const props = defineProps({
  positionOptions: {
    type: Array,
  },
});

const toggleModal = () => {
  document.getElementById("createTypeModal").classList.toggle("modal-open");
  typeName.value = null;
  selectedPosition.value = null;
};

const clickCreateTypeBtn = async () => {
  console.log(selectedPosition.value);
  console.log(typeName.value);
  if (!selectedPosition.value) {
    window.alert("직무를 선택해주세요.");
    positionElement.value.focus();
    return;
  }

  if (!typeName.value) {
    window.alert("유형을 입력해주세요.");
    typeNameElement.value.focus();
    return;
  }
  const requestData = {
    positionId: selectedPosition.value,
    questionTypeName: typeName.value,
  };
  await createTypes(requestData);
  alert("유형 생성이 완료 되었습니다.");
  emit("init");
  toggleModal();
};

defineExpose({
  toggleModal,
});
</script>