# post-ide-dsm-wrapper
DSM-wrapper, адаптированный под работу с DSM-менеджером

# Ссылки
- Исходный проект (Влад Башев): https://github.com/v-bashev/dsm_wrapper
- DSM-manager: https://github.com/Zheltog/post-ide-dsm-manager
- Ядро языка poST: https://github.com/v-bashev/post_core
- Пример с генераторами в ST и XML: https://github.com/v-bashev/post_to_st

# Как использовать (актуально для исходного wrapper'а)
Склонировать проект (или просто скачать архив), переименовать под имя своего DSM, закинуть в корень проекта jar со своим генератором (jar с st генератором можно удалить), исправить 4 TODO в файлах: build.gradle, AppLauncher и WrappedServer, вызвать `./gradlew jar`, сгенерированный jar с вашим dsm будет лежать в `build/libs`
