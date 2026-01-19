const TIMELINE = [
  "PLACED",
  "CONFIRMED",
  "PACKED",
  "OUT_FOR_DELIVERY",
  "DELIVERED"
];

export default function OrderTimeline({ currentStatus }) {
  return (
    <div style={{ display: "flex", marginTop: 10 }}>
      {TIMELINE.map(status => {
        const active = TIMELINE.indexOf(status) <= TIMELINE.indexOf(currentStatus);
        return (
          <div
            key={status}
            style={{
              padding: "6px 10px",
              marginRight: 8,
              borderRadius: 4,
              background: active ? "#4caf50" : "#ccc",
              color: "white",
              fontSize: 12
            }}
          >
            {status}
          </div>
        );
      })}
    </div>
  );
}
