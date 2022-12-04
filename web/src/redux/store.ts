import { configureStore, ThunkAction, Action } from '@reduxjs/toolkit';
import AuthReducer from './authStore';
import NotificationReducer from './notificationStore'
import UserReducer from './notificationStore'

export const store = configureStore({
  reducer: {
    auth: AuthReducer,
    notify: NotificationReducer,
    user:UserReducer
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
