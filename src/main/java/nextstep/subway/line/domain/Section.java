package nextstep.subway.line.domain;

import java.util.Objects;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import nextstep.subway.common.BaseEntity;
import nextstep.subway.common.ErrorCode;
import nextstep.subway.line.exception.SectionException;
import nextstep.subway.station.domain.Station;
import nextstep.subway.station.dto.StationResponse;

@Entity
public class Section extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "line_id")
	private Line line;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "up_station_id")
	private Station upStation;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "down_station_id")
	private Station downStation;

	@Embedded
	private Distance distance;

	protected Section() {
	}

	public Section(Line line, Station upStation, Station downStation, int distance) {
		this.line = line;
		this.upStation = upStation;
		this.downStation = downStation;
		this.distance = new Distance(distance);
	}

	public Line getLine() {
		return line;
	}

	public Station getUpStation() {
		return upStation;
	}

	public Station getDownStation() {
		return downStation;
	}

	public StationResponse getUpStationResponse() {
		return StationResponse.of(upStation);
	}

	public StationResponse getDownStationResponse() {
		return StationResponse.of(downStation);
	}

	public Distance getDistance() {
		return distance;
	}

	public void validSection(Section section) {
		validNotInStations(section);
		validSameStation(section);
		isInDistance(section);
	}

	private void isInDistance(Section section) {
		if (distance.getDistance() < section.distance.getDistance()
			|| distance.getDistance() == section.distance.getDistance()) {
			throw new SectionException(ErrorCode.VALID_DISTANCE_ERROR);
		}
	}

	private boolean isSameUpStation(Section section) {
		return upStation.equals(section.upStation);
	}

	private boolean isSameDownStation(Section section) {
		return downStation.equals(section.downStation);
	}

	private boolean isSameUpDownStation(Section section) {
		return isSameUpStation(section) && isSameDownStation(section);
	}

	private void validSameStation(Section section) {
		if (isSameUpDownStation(section)) {
			throw new SectionException(ErrorCode.VALID_SAME_STATION_ERROR);
		}
	}

	private boolean isInStations(Section section) {
		return isSameUpStation(section) || isSameDownStation(section);
	}

	private void validNotInStations(Section section) {
		if (!isInStations(section)) {
			throw new SectionException(ErrorCode.VALID_NOT_IN_STATIONS_ERROR);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Section section = (Section)o;
		return getId().equals(section.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(line, upStation, downStation, distance);
	}
}
