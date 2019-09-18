import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './mahasiswa-my-suffix.reducer';
import { IMahasiswaMySuffix } from 'app/shared/model/mahasiswa-my-suffix.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMahasiswaMySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IMahasiswaMySuffixUpdateState {
  isNew: boolean;
}

export class MahasiswaMySuffixUpdate extends React.Component<IMahasiswaMySuffixUpdateProps, IMahasiswaMySuffixUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { mahasiswaEntity } = this.props;
      const entity = {
        ...mahasiswaEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/mahasiswa-my-suffix');
  };

  render() {
    const { mahasiswaEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="etfTestApp.mahasiswa.home.createOrEditLabel">
              <Translate contentKey="etfTestApp.mahasiswa.home.createOrEditLabel">Create or edit a Mahasiswa</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : mahasiswaEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="mahasiswa-my-suffix-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="mahasiswa-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nimLabel" for="mahasiswa-my-suffix-nim">
                    <Translate contentKey="etfTestApp.mahasiswa.nim">Nim</Translate>
                  </Label>
                  <AvField
                    id="mahasiswa-my-suffix-nim"
                    type="text"
                    name="nim"
                    validate={{
                      minLength: { value: 5, errorMessage: translate('entity.validation.minlength', { min: 5 }) },
                      maxLength: { value: 5, errorMessage: translate('entity.validation.maxlength', { max: 5 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="namaLabel" for="mahasiswa-my-suffix-nama">
                    <Translate contentKey="etfTestApp.mahasiswa.nama">Nama</Translate>
                  </Label>
                  <AvField
                    id="mahasiswa-my-suffix-nama"
                    type="text"
                    name="nama"
                    validate={{
                      maxLength: { value: 50, errorMessage: translate('entity.validation.maxlength', { max: 50 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="alamatLabel" for="mahasiswa-my-suffix-alamat">
                    <Translate contentKey="etfTestApp.mahasiswa.alamat">Alamat</Translate>
                  </Label>
                  <AvField
                    id="mahasiswa-my-suffix-alamat"
                    type="text"
                    name="alamat"
                    validate={{
                      maxLength: { value: 500, errorMessage: translate('entity.validation.maxlength', { max: 500 }) }
                    }}
                  />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/mahasiswa-my-suffix" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  mahasiswaEntity: storeState.mahasiswa.entity,
  loading: storeState.mahasiswa.loading,
  updating: storeState.mahasiswa.updating,
  updateSuccess: storeState.mahasiswa.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MahasiswaMySuffixUpdate);
