# post_dsm_wrapper
New DSM-wrapper, adapted for DSM-manager

## References
- Instructions for working with the poST core: https://github.com/v-bashev/post_core/wiki 
- poST core: https://github.com/v-bashev/post_core
- DSM manager: https://github.com/Zheltog/post-ide-dsm-manager
- poST to ST generation module: https://github.com/v-bashev/post_to_st 
- poST to ST web transtator: http://post2st.iae.nsk.su
- poST web IDE: http://post.iae.nsk.su

## Build DSM
1. Replace `dsm-core.jar` with your generator jar file
2. Replace property from `TODO` section
2. Execute gradle command: `./gradlew bootJar`
3. Done, your DSM will be located in `build\libs/*.jar'

## TODO
Change property files with our actual data:
- `src\main\resources\dsm.properties`
  - **dsm.name** - DSM name
  - **dsm.executorClassName** - main generator class extended from `IDsmExecutor`
- `src\main\resources\log4j.properties`
  - **log4j.appender.file.File** - log file location
