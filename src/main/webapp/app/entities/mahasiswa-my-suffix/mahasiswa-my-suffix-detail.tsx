import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './mahasiswa-my-suffix.reducer';
import { IMahasiswaMySuffix } from 'app/shared/model/mahasiswa-my-suffix.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMahasiswaMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class MahasiswaMySuffixDetail extends React.Component<IMahasiswaMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { mahasiswaEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="etfTestApp.mahasiswa.detail.title">Mahasiswa</Translate> [<b>{mahasiswaEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nim">
                <Translate contentKey="etfTestApp.mahasiswa.nim">Nim</Translate>
              </span>
            </dt>
            <dd>{mahasiswaEntity.nim}</dd>
            <dt>
              <span id="nama">
                <Translate contentKey="etfTestApp.mahasiswa.nama">Nama</Translate>
              </span>
            </dt>
            <dd>{mahasiswaEntity.nama}</dd>
            <dt>
              <span id="alamat">
                <Translate contentKey="etfTestApp.mahasiswa.alamat">Alamat</Translate>
              </span>
            </dt>
            <dd>{mahasiswaEntity.alamat}</dd>
          </dl>
          <Button tag={Link} to="/entity/mahasiswa-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/mahasiswa-my-suffix/${mahasiswaEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ mahasiswa }: IRootState) => ({
  mahasiswaEntity: mahasiswa.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MahasiswaMySuffixDetail);
