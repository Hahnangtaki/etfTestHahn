import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IMahasiswaMySuffix } from 'app/shared/model/mahasiswa-my-suffix.model';
import { getEntities as getMahasiswas } from 'app/entities/mahasiswa-my-suffix/mahasiswa-my-suffix.reducer';
import { getEntity, updateEntity, createEntity, reset } from './buku-my-suffix.reducer';
import { IBukuMySuffix } from 'app/shared/model/buku-my-suffix.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBukuMySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IBukuMySuffixUpdateState {
  isNew: boolean;
  mahasiswaId: string;
}

export class BukuMySuffixUpdate extends React.Component<IBukuMySuffixUpdateProps, IBukuMySuffixUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      mahasiswaId: '0',
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

    this.props.getMahasiswas();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { bukuEntity } = this.props;
      const entity = {
        ...bukuEntity,
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
    this.props.history.push('/entity/buku-my-suffix');
  };

  render() {
    const { bukuEntity, mahasiswas, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="etfTestApp.buku.home.createOrEditLabel">
              <Translate contentKey="etfTestApp.buku.home.createOrEditLabel">Create or edit a Buku</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : bukuEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="buku-my-suffix-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="buku-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="judulBukuLabel" for="buku-my-suffix-judulBuku">
                    <Translate contentKey="etfTestApp.buku.judulBuku">Judul Buku</Translate>
                  </Label>
                  <AvField
                    id="buku-my-suffix-judulBuku"
                    type="text"
                    name="judulBuku"
                    validate={{
                      maxLength: { value: 100, errorMessage: translate('entity.validation.maxlength', { max: 100 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="kategoriLabel" for="buku-my-suffix-kategori">
                    <Translate contentKey="etfTestApp.buku.kategori">Kategori</Translate>
                  </Label>
                  <AvField
                    id="buku-my-suffix-kategori"
                    type="text"
                    name="kategori"
                    validate={{
                      maxLength: { value: 50, errorMessage: translate('entity.validation.maxlength', { max: 50 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="buku-my-suffix-mahasiswa">
                    <Translate contentKey="etfTestApp.buku.mahasiswa">Mahasiswa</Translate>
                  </Label>
                  <AvInput id="buku-my-suffix-mahasiswa" type="select" className="form-control" name="mahasiswaId">
                    <option value="" key="0" />
                    {mahasiswas
                      ? mahasiswas.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/buku-my-suffix" replace color="info">
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
  mahasiswas: storeState.mahasiswa.entities,
  bukuEntity: storeState.buku.entity,
  loading: storeState.buku.loading,
  updating: storeState.buku.updating,
  updateSuccess: storeState.buku.updateSuccess
});

const mapDispatchToProps = {
  getMahasiswas,
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
)(BukuMySuffixUpdate);
