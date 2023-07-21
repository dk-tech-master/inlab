<template>
  <slot name="header"></slot>
  <slot name="body">
    <div class="flex gap-x-4">
      <div>
        <label for="job" class="block mb-2 text-base font-bold text-gray-700"
          >직무</label
        >
        <select
          class="font-medium select select-primary border-gray-300 max-w-xs"
          v-model="searchInfo.position"
          @change="changePosition()"
        >
          <option value="">All</option>
          <option
            v-for="(option, index) in positionOptions"
            :key="index"
            :value="option.positionId"
          >
            {{ option.positionName }}
          </option>
        </select>
      </div>
      <div>
        <label
          for="questionType"
          class="block mb-2 text-base font-bold text-gray-700"
          >유형</label
        >
        <select
          class="w-32 font-medium select select-primary border-gray-300 max-w-xs"
          v-model="searchInfo.type"
          :disabled="!searchInfo.position"
        >
          <option value="">All</option>
          <option
            v-for="(option, index) in typeOptions"
            :key="index"
            :value="option?.questionTypeId"
          >
            {{ option?.questionTypeName }}
          </option>
        </select>
      </div>
      <div>
        <label
          for="questionLevel"
          class="block mb-2 text-base font-bold text-gray-700"
          >난이도</label
        >
        <select
          class="w-32 font-medium select select-primary border-gray-300 max-w-xs"
          v-model="searchInfo.level"
          :disabled="!searchInfo.position"
        >
          <option value="">All</option>
          <option
            v-for="(option, index) in levelOptions"
            :key="index"
            :value="option.levelId"
          >
            {{ option?.levelName }}
          </option>
        </select>
      </div>
      <div>
        <label
          for="nickName"
          class="block mb-2 text-base font-bold text-gray-700"
          >제목</label
        >
        <input
          v-model="searchInfo.title"
          type="text"
          name="title"
          class="p-2.5 input input-bordered border-gray-300 text-sm"
          placeholder="제목을 입력하세요"
          required
          @keyup.enter="onSearch"
        />
      </div>
      <button class="btn btn-primary self-end" @click="onSearch">검색</button>
    </div>
  </slot>
</template>
<script setup>
import { ref } from "vue";
import { getpositionOption, getTypeOption } from "@/api/select";

const searchInfo = ref({
  position: "",
  type: "",
  level: "",
  title: "",
});

const positionOptions = ref([]);
const levelOptions = ref([]);
const typeOptions = ref([]);

const init = async () => {
  const positionOptionInfo = await getpositionOption();
  positionOptions.value = positionOptionInfo.data;
};

init();

const changePosition = async () => {
  searchInfo.value.type = "";
  searchInfo.value.level = "";
  const selectedOption = searchInfo.value.position;
  levelOptions.value = positionOptions?.value.find(
    (option) => option.positionId === selectedOption,
  )?.levelListDto;
  const requestData = {
    positionId: selectedOption,
  };
  const typeOptionInfo = await getTypeOption(requestData);
  typeOptions.value = typeOptionInfo.data;
};

const emit = defineEmits(["search-info"]);

const onSearch = () => {
  //검색 이벤트 발생
  emit("search-info", searchInfo.value);
};
</script>
