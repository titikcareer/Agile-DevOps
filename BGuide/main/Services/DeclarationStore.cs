namespace main.Services;
public record DeclarationRecord(
    string PicId,
    string PicName,
    string Department,
    string SafetyDeclaration,
    string ValueDeclaration,
    string? PhotoFileName,
    string? VideoFileName,
    DateTime SubmittedAtUtc);

public record AgentTally(string PicId, string PicName, int SubmissionCount);

public record BestAgentOfMonth(DateOnly Month, IReadOnlyList<AgentTally> TopAgents, int TotalSubmissions);

/// <summary>
/// In-memory, process-wide store of submitted declarations.
/// Register as a Singleton in Program.cs:
///     builder.Services.AddSingleton&lt;DeclarationStore&gt;();
///
/// Any component (e.g. ValorAgent.razor, Dashboard.razor) can inject this
/// and subscribe to <see cref="Changed"/> to react in real time to new
/// submissions coming from ANY user's session — that's what makes the
/// Dashboard table and the "Best Agent" panel update live without a
/// page refresh, across every connected Blazor Server circuit.
/// </summary>
public class DeclarationStore
{
    private readonly object _lock = new();
    private readonly List<DeclarationRecord> _records = new();
    private const int MaxStoredRecords = 2000;
    public event Action? Changed;

    public void Add(DeclarationRecord record)
    {
        lock (_lock)
        {
            _records.Add(record);

            if (_records.Count > MaxStoredRecords)
            {
                _records.RemoveRange(0, _records.Count - MaxStoredRecords);
            }
        }

        Changed?.Invoke();
    }

    public IReadOnlyList<DeclarationRecord> GetAll()
    {
        lock (_lock)
        {
            return _records
                .OrderByDescending(r => r.SubmittedAtUtc)
                .ToList();
        }
    }
    public IReadOnlyList<BestAgentOfMonth> GetBestAgentsByMonth()
    {
        List<DeclarationRecord> snapshot;
        lock (_lock)
        {
            snapshot = _records.ToList();
        }

        return snapshot
            .GroupBy(r => new DateOnly(r.SubmittedAtUtc.Year, r.SubmittedAtUtc.Month, 1))
            .OrderByDescending(g => g.Key)
            .Select(monthGroup =>
            {
                var tallies = monthGroup
                    .GroupBy(r => (r.PicId, r.PicName))
                    .Select(picGroup => new AgentTally(picGroup.Key.PicId, picGroup.Key.PicName, picGroup.Count()))
                    .ToList();

                var maxCount = tallies.Max(t => t.SubmissionCount);

                var topAgents = tallies
                    .Where(t => t.SubmissionCount == maxCount)
                    .OrderBy(t => t.PicName, StringComparer.OrdinalIgnoreCase)
                    .ToList();

                return new BestAgentOfMonth(monthGroup.Key, topAgents, monthGroup.Count());
            })
            .ToList();
    }
}