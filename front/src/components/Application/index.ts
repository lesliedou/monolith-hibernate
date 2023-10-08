import appLogo from './src/AppLogo.vue';
import appProvider from './src/AppProvider.vue';
import appSearch from './src/search/AppSearch.vue';
import appDarkModeToggle from './src/AppDarkModeToggle.vue';
import { withInstall } from '@/utils';

// begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！

export { useAppProviderContext } from './src/useAppContext';

export const AppLogo = withInstall(appLogo);
export const AppProvider = withInstall(appProvider);
export const AppSearch = withInstall(appSearch);
export const AppDarkModeToggle = withInstall(appDarkModeToggle);
