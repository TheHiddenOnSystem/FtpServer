import { configureStore, ThunkAction, Action } from '@reduxjs/toolkit';
import AuthReducer from './storage/authStore';
import ExplorerReducer from './storage/exploreStore';
import NotificationReducer from './storage/notificationStore'
import UserReducer from './storage/userStore'

export const store = configureStore({
  reducer: {
    auth: AuthReducer,
    notify: NotificationReducer,
    user: UserReducer,
    explorer: ExplorerReducer
  }
}
//,applyMiddleware(thunk)
)

export type AppDispatch = typeof store.dispatch;
export type RootState = ReturnType<typeof store.getState>;
export type AppThunk<ReturnType = void> = ThunkAction<
  ReturnType,
  RootState,
  unknown,
  Action<string>
>;
