package nextstep.subway.line.dto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import nextstep.subway.line.domain.Line;
import nextstep.subway.station.dto.StationResponse;

public class LineResponse {
	private final Long id;
	private final String name;
	private final String color;
	private final List<StationResponse> stations;
	private final LocalDateTime createdDate;
	private final LocalDateTime modifiedDate;

	private LineResponse(final Long id, final String name, final String color, final List<StationResponse> stations,
		final LocalDateTime createdDate,
		final LocalDateTime modifiedDate) {
		this.id = id;
		this.name = name;
		this.color = color;
		this.stations = Collections.unmodifiableList(stations);
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
	}

	public static LineResponse of(final Line line) {
		return new LineResponse(line.getId(), line.getName(), line.getColor(), line.getStations(),
			line.getCreatedDate(),
			line.getModifiedDate());
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}

	public List<StationResponse> getStations() {
		return stations;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

}
