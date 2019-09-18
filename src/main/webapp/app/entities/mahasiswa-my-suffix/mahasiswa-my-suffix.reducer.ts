import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMahasiswaMySuffix, defaultValue } from 'app/shared/model/mahasiswa-my-suffix.model';

export const ACTION_TYPES = {
  FETCH_MAHASISWA_LIST: 'mahasiswa/FETCH_MAHASISWA_LIST',
  FETCH_MAHASISWA: 'mahasiswa/FETCH_MAHASISWA',
  CREATE_MAHASISWA: 'mahasiswa/CREATE_MAHASISWA',
  UPDATE_MAHASISWA: 'mahasiswa/UPDATE_MAHASISWA',
  DELETE_MAHASISWA: 'mahasiswa/DELETE_MAHASISWA',
  RESET: 'mahasiswa/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMahasiswaMySuffix>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type MahasiswaMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: MahasiswaMySuffixState = initialState, action): MahasiswaMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MAHASISWA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MAHASISWA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MAHASISWA):
    case REQUEST(ACTION_TYPES.UPDATE_MAHASISWA):
    case REQUEST(ACTION_TYPES.DELETE_MAHASISWA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_MAHASISWA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MAHASISWA):
    case FAILURE(ACTION_TYPES.CREATE_MAHASISWA):
    case FAILURE(ACTION_TYPES.UPDATE_MAHASISWA):
    case FAILURE(ACTION_TYPES.DELETE_MAHASISWA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_MAHASISWA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_MAHASISWA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MAHASISWA):
    case SUCCESS(ACTION_TYPES.UPDATE_MAHASISWA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MAHASISWA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/mahasiswas';

// Actions

export const getEntities: ICrudGetAllAction<IMahasiswaMySuffix> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MAHASISWA_LIST,
    payload: axios.get<IMahasiswaMySuffix>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IMahasiswaMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MAHASISWA,
    payload: axios.get<IMahasiswaMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMahasiswaMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MAHASISWA,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMahasiswaMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MAHASISWA,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMahasiswaMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MAHASISWA,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
