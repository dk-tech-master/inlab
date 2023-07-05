import "./style.css";

import { createApp } from "vue";
import { createPinia } from "pinia";

import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faFaceFrown } from "@fortawesome/free-regular-svg-icons";
import { faRecycle } from "@fortawesome/free-solid-svg-icons";

import App from "./App.vue";
import router from "./router";

const app = createApp(App);
library.add(faFaceFrown, faRecycle);

app.use(createPinia());
app.use(router);
app.component("font-awesome-icon", FontAwesomeIcon).mount("#app");
app.component("font-awesome-icon", FontAwesomeIcon).mount("#teleport-area");

// import { fas로 시작하는 아이콘명*설명보기*} from '@fortawesome/free-solid-svg-icons'
// import { far로 시작하는 아이콘명*설명보기* } from '@fortawesome/free-regular-svg-icons'
// import { fab로 시작하는 아이콘명*설명보기* } from '@fortawesome/free-brands-svg-icons'
