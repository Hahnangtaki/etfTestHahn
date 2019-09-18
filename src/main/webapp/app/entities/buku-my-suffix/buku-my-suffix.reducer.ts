import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBukuMySuffix, defaultValue } from 'app/shared/model/buku-my-suffix.model';

export const ACTION_TYPES = {
  FETCH_BUKU_LIST: 'buku/FETCH_BUKU_LIST',
  FETCH_BUKU: 'buku/FETCH_BUKU',
  CREATE_BUKU: 'buku/CREATE_BUKU',
  UPDATE_BUKU: 'buku/UPDATE_BUKU',
  DELETE_BUKU: 'buku/DELETE_BUKU',
  RESET: 'buku/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBukuMySuffix>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type BukuMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: BukuMySuffixState = initialState, action): BukuMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BUKU_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BUKU):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_BUKU):
    case REQUEST(ACTION_TYPES.UPDATE_BUKU):
    case REQUEST(ACTION_TYPES.DELETE_BUKU):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_BUKU_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BUKU):
    case FAILURE(ACTION_TYPES.CREATE_BUKU):
    case FAILURE(ACTION_TYPES.UPDATE_BUKU):
    case FAILURE(ACTION_TYPES.DELETE_BUKU):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_BUKU_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_BUKU):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_BUKU):
    case SUCCESS(ACTION_TYPES.UPDATE_BUKU):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_BUKU):
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

const apiUrl = 'api/bukus';

// Actions

export const getEntities: ICrudGetAllAction<IBukuMySuffix> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_BUKU_LIST,
  payload: axios.get<IBukuMySuffix>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IBukuMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BUKU,
    payload: axios.get<IBukuMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IBukuMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BUKU,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBukuMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BUKU,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBukuMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BUKU,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
